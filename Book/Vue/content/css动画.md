### 引入初始化配置  
```
import 'normalize.css';
```
### 动画
```css
@keyframes bounce-in {//定义动画
    0% {
      background-color: aquamarine;
      filter: blur(50px);
    }
    50% {
      background-color: red;
      filter: blur(0px);
    }
    100% {
     background-color: olivedrab;
      filter: blur(100px);
    }
  }
 ```
 > 创建class
 ```css
   .play {
    animation: bounce-in 5s infinite ease;
  }
  .restart {
    animation: bounce-in 5s infinite ease;
  }
  .pause {
    animation-play-state: paused;
  }
  ```
  ```js
  this.show = (this.show === 'pause play')?'restart':'pause play';
  ```