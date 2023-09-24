# css单位

1. px：像素
2. em：相对于父元素的字体大小
3. rem：相对于根元素的字体大小
4. vw：相对于视口的宽度，视口被均分为100单位的vw
5. vh：相对于视口的高度，视口被均分为100单位的vh
6. vmin：相对于视口的宽度和高度中较小的那个，视口被均分为100单位的vmin
7. vmax：相对于视口的宽度和高度中较大的那个，视口被均分为100单位的vmax
8. %：相对于父元素的**宽度**
9. ex：相对于字体的x高度
10. ch：相对于数字0的宽度

# 背景图片

```
.login_container {
  width: 100%;
  height: 100vh;
  background: url('@/assets/images/background.jpg') no-repeat;
  background-size: cover;
}
```

# 使用的属性

## 过度动画

> 过度动画的属性是transition，它有四个属性值，分别是：过渡属性、过渡时间、过渡速度曲线、延迟时间
> 
> 过渡属性：all表示所有属性都过渡，也可以指定某个属性过渡
> 
> 过渡时间：单位是s，表示过渡的时间

```
transition: all 0.3s;
```

## 自动添加滚动条

> 当内容超出容器的大小时，自动添加滚动条

```
overflow: auto;
```

# 页面适配问题

## vw，vh

> vw：相对于视口的宽度，视口被均分为100单位的vw
vh：相对于视口的高度，视口被均分为100单位的vh
问题：echarts中文字不支持该单位！！！无法自适应

## scale

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
<div class="container">
    <div class="box">
        <div class="top">top</div>
        <div class="bottom">foot</div>
    </div>
</div>

<style>
    * {
        margin: 0;
        padding: 0;
    }

    .container {
        width: 100vw;
        height: 100vh;
        background-color: red;
    }

    .box {
        width: 1920px;
        height: 1080px;
        background-color: blue;
        position: fixed;
        transform-origin: left top;
        left: 50%;
        top: 50%;
    }

    .top {
        width: 100px;
        height: 100px;
        background-color: yellow;
        margin-left: 50px;
    }

    .bottom {
        width: 100px;
        height: 100px;
        background-color: green;
        margin-left: 50px;
        margin-top: 100px;
    }
</style>
</body>
</html>

<script>
    // 控制放大缩小
    let box = document.querySelector('.box')
    box.style.transform = `scale(${getScale()}) translate(-50%, -50%)`

    // 计算缩放的比例
    function getScale(w = 1920, h = 1080) {
        let ww = window.innerWidth / w
        let wh = window.innerHeight / h
        return ww < wh ? ww : wh
    }

    window.onresize = function () {
        box.style.transform = `scale(${getScale()}) translate(-50%, -50%)`
    }
</script>
```
