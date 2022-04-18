server {
    listen 7081;
    location /sayhello {
        default_type text/plain;
        content_by_lua_block {
            ngx.say("hello nginx!!")
        }
    }
}