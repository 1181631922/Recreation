# 视频列表实现
这里我自己架设了一个服务器，从服务器取数据，本地播放。

### 1.播放器还是先看一下ijkplayer，我用的是ijkplayer播放的，剩下的就比较琐碎了<br>
* [1.简单说一下编译ijkplayer](http://blog.csdn.net/qq_23195583/article/details/52605679)
* [2.使用ijkplayer进行视频播放](http://blog.csdn.net/qq_23195583/article/details/52621601)
* [3.Fresco进行图片的裁剪以及加水印](http://blog.csdn.net/qq_23195583/article/details/53582706)
* [4.视频列表的android客户端和springmvc服务端实现（三）](http://blog.csdn.net/qq_23195583/article/details/54343336)

### 2.这里简单贴一下播放视频的activity的一些重要代码<br>
```
//去掉系统标题栏
getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
```
其他的可以看以上的博客，里面写的比较详细。
### 3.关于列表
我在列表中用过，但是机型适配有些问题，往ijkplayer提交过issue，没有得到解答，暂时就没有做直接在列表中进行播放。
