##使用超级简单

##步骤

1.导入

2.使用示例在pages.json中添加
```json

{
	 "path" : "uni_modules/gb-spcode/pages/gb-spread-code",
	 "style" :                                                                                    
	 {
	     "navigationBarTitleText": "推广二维码",
	     "enablePullDownRefresh": false
	 }
		    
}

```

3.跳转到该页面
```javascript

// 链接
let link = ''
// 背景图
let img = ''
// 邀请码
let code = ''

uni.navigateTo({url:'/uni_modules/gb-spcode/pages/gb-spread-code?link=' + link + '&img=' + img  + '&code=' + code})

```

4.完毕（如果需要改变样式啥的  自己在 uni_modules/gb-spcode/pages/gb-spread-code.vue  这里面随便改）