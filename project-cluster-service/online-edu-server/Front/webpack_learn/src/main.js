const common = require('./common');
const utils = require('./utils');
// 引入css文件
require('./style.css')

common.info("hello webpack" + utils.add(1,2))
