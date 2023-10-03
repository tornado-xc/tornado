-- 自增id key
local key = KEYS[1]
-- 获取步长
local step = tonumber(ARGV[1])
-- 获取多少个
local count = tonumber(ARGV[2])

-- 结果
local result = {}

for i = 1, count do
    local incr = redis.call("incrby", key, step)
    result[i] = incr
end

return result;
