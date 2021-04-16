//es6引入模块  第一种
// 引入01.js
import {getList,save} from './01.js'

// 调用
getList()
save()

// 测试  终端执行 node .\02.js

/*-----------------------------------------------------------------**/
// 第二种 **
// 引入
import m from './01.js'

// 调用方法
m.getList()
m.save()
