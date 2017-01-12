# 程序启动页
程序首次启动一般都会有个引导页，这里我也按照一般的用户习惯做了个。

### 1.先看一下相关资料和博客
* [1.谷歌的关于viewpage+fragment的示例代码，需要梯子](https://developer.android.com/training/implementing-navigation/lateral.html)
* [2.android客户端首次进入轮播图](http://blog.csdn.net/qq_23195583/article/details/54379232)
* [3.android material design之Tablayout，Recyclerview，Fragment，Viewpager搭配使用（四）](http://blog.csdn.net/qq_23195583/article/details/51692578)

### 2.使用的核心代码
1.确定初始点的位置
```
DisplayMetrics displayMetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        final int width = displayMetrics.widthPixels;
        maxX = width;
        Matrix matrix = new Matrix();
        matrix.postTranslate(width / (totalSize + 1) - MyUtils.dip2px(this, 4), 0);
        ivMovementCircle.setImageMatrix(matrix);
```
2.指示器点的偏移
```
startViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (maxX != 0 && positionOffsetPixels != 0) {
                    float alph = 255 * positionOffsetPixels / maxX;
                    if (position == totalSize - 2) {
                        btnFirstStart.setVisibility(View.VISIBLE);
                        btnFirstStart.getBackground().setAlpha((int) alph);
                        btnFirstStart.setAlpha(alph);
                    } else {
                        btnFirstStart.setVisibility(View.GONE);
                    }
                }
                if (positionOffsetPixels != 0) {
                    ViewCompat.setTranslationX(ivMovementCircle, maxX / (totalSize + 1) * position + positionOffsetPixels / (totalSize + 1));
                }

            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
```
### 3.再看一下效果图：
![效果图](https://github.com/1181631922/Recreation/blob/master/readme/start/start.gif)