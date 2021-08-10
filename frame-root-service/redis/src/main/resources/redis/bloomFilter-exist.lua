--- 获取key
local bloomName = KEYS[1]
--- 获取value
local value = KEYS[2]
local result = redis.call('BF.EXISTS', bloomName, value)
return result