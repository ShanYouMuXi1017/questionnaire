 **组件名：lfj-dropdown-box**
> 代码块： `lfj-dropdown-box`

本插件基于官方插件 [uni-data-select](https://ext.dcloud.net.cn/plugin?id=7993) 进行二次开发拓展功能，支持uni-data-select本身功能不变（表单验证等）

<h1>拓展功能</h1>
<ol>
<li>单选</li>
<li>多选</li>
<li>搜索</li>
<li>下拉加载</li>
<li>右侧文字</li>
</ol>

<h2>API</h2>
### lfj-dropdown-box Props

|  属性名		|    类型				| 默认值																	| 说明																									|
| -				| -						| -																			| -																										|
| v-model		| String、Array、Number	|-																			| 选中项绑定值																							|
| multiple		| Boolean				| false																		| 是否多选																								|
| disabled		| Boolean				|false																		| 是否禁用																								|
| rightTextShow	| Boolean				|false																		|是否显示右侧文字																						|
| map			| Array					| {text: 'text', value: 'value', rightText: 'rightText', disable: 'disable'}| 字段映射，将text/value映射到数据中的其他字段															|
| filterable	| Boolean				| false																		|  是否开启搜索																							||
| localdata		| Array					| -																			| 下拉列表本地数据																						|
|placeholder	| String				| "请选择"																	| 输入框的提示文字																						|
|emptyTips		| String				|"无选项"																	| 无选项提示																							|
|clear			| Boolean				| true																		| 是否清空																								|
|format			| String				| -																			| 格式化输出 用法 field="_id as value, version as text, uni_platform as label" format="{label} - {text}"|
|@change		| event					| event(String)																| 选中发生变化触发																						|
|@lower			| event					| event(String)																| 触底事件																								|


## 示例
 ```
	<lfj-dropdown-box
	v-model="value3"
	placeholder="请选择内容"
	dropDownLoading
	rightTextShow
	multiple
	:loadStatus="loadStatus"
	:localdata="localdata"
	@lower="lower"
	@change="change"
></lfj-dropdown-box>

lower() {
	// 模拟接口数据
	let data = [
		{
			text: '新增数据1',
			value: '1ewq',
			rightText: 'rightText'
		},
		{
			text: '新增数据2',
			value: '2qwr',
			rightText: 'rightText'
		},
		{
			text: '新增数据3',
			value: '32weq',
			rightText: 'rightText'
		}
	];
	let pageNum = Math.ceil(this.total / this.pageSize);
	if (this.pageNum < pageNum) {
		this.pageNum++;
		this.loadStatus = 'loading';
		setTimeout(() => {
			this.localdata.push(...data);
		}, 2000);
	} else {
		this.loadStatus = 'nomore';
	}
},
change(e) {
	console.log('回显的数据', e);
},
	
```

