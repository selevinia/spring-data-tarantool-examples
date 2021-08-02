box.cfg { listen = 3301 }

box.once('schema', function()
    local users = box.schema.space.create("users", { if_not_exists = true })
    users:format({
        { name = "id", type = "uuid" },
        { name = "first_name", type = "string" },
        { name = "last_name", type = "string" },
        { name = "birth_date", type = "integer" },
        { name = "email", type = "string", is_nullable = true },
        { name = "address", type = "string", is_nullable = true }
    })
    users:create_index("primary", { parts = { { field = "id" } },
                                    if_not_exists = true })
    users:create_index("last_name", { parts = { { field = "last_name" } },
                                      unique = false,
                                      if_not_exists = true })
    users:create_index("first_name_last_name", { parts = { { field = "first_name" }, { field = "last_name" } },
                                                 unique = false,
                                                 if_not_exists = true })
    users:create_index("birth_date", { parts = { { field = "birth_date" } },
                                       unique = false,
                                       if_not_exists = true })

    local versioned = box.schema.space.create("versioned_users", { if_not_exists = true })
    versioned:format({
        { name = "id", type = "uuid" },
        { name = "first_name", type = "string" },
        { name = "last_name", type = "string" },
        { name = "version", type = "unsigned" },
    })
    versioned:create_index("primary", { parts = { { field = "id" } },
                                        if_not_exists = true })

    local audited = box.schema.space.create("audited_users", { if_not_exists = true })
    audited:format({
        { name = "id", type = "uuid" },
        { name = "first_name", type = "string" },
        { name = "last_name", type = "string" },
        { name = "created_by", type = "string" },
        { name = "created_date", type = "unsigned" },
        { name = "last_modified_by", type = "string" },
        { name = "last_modified_date", type = "unsigned" },
    })
    audited:create_index("primary", { parts = { { field = "id" } },
                                      if_not_exists = true })
end)

function find_users_by_last_name(name)
    return box.space.users.index.last_name:select(name)
end

function count_users_by_last_name(name)
    return box.space.users.index.last_name:count(name)
end
