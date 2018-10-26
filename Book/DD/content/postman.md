## token变量
在token请求页面，添加tests参数
```js
var jsonData = JSON.parse(responseBody);
postman.setGlobalVariable("token", jsonData.access_token);
```
在引用页面调用{{token}}
