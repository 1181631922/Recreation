# 首页获取内涵段子数据

### 1.软件的安装与使用
* 1.先去charles官网下载软件进行安装。[官网地址](https://www.charlesproxy.com/)
* 2.对https请求的支持，我这里在csdn上有写，但是是3.x的版本和4.x差不多。[ 使用Charles抓https请求包](http://blog.csdn.net/qq_23195583/article/details/52087189)
* 3.手机进行代理设置长按wifi会出现修改网络就可以了。
### 2.相应的博客介绍
* [1.使用Charles抓https请求包](http://blog.csdn.net/qq_23195583/article/details/52087189)
* [2.通过抓包获取内涵段子的接口](http://blog.csdn.net/qq_23195583/article/details/54287167)


### 3.对内涵段子首页数据的抓取
* 分析一下首页的接口，我把接口直接放到chrome里面可以请求下来，基本上排除了header的验证，剩下的就是分析接口的首次请求，刷新，加载更多<br>
![首页请求](https://github.com/1181631922/Recreation/blob/master/readme/mainpage/DF19396E-AA8B-4CF3-87FF-E6D508D864C5.png)<br>
红色的标记的是不同点，经过验证确实是而且传的是当前的long型的秒时间。

* 接下来就是分析数据结构。
```
{
			"display_time": 1483927634,
			"group": {
				"has_comments": 0,
				"open_url": "http://ic.snssdk.com/neihan/in_app/essence_detail/6373177732877074690/?item_id=6373183216039232002&refer=click_feed",
				"large_image": {
					"width": 400,
					"url_list": [{
						"url": "http://p3.pstatp.com/w400/150d0000ec7e5937f9ca"
					}, {
						"url": "http://pb2.pstatp.com/w400/150d0000ec7e5937f9ca"
					}, {
						"url": "http://pb3.pstatp.com/w400/150d0000ec7e5937f9ca"
					}],
					"uri": "w400/150d0000ec7e5937f9ca",
					"height": 279
				},
				"title": "\u7206\u7b11GIF\u96c6\u9526\uff1a\u59b9\u5b50\u98d9\u8f66\u5566\uff0c\u62ff\u51fa\u79c1\u85cf\u591a\u5e74\u7684\u795e\u79d8\u9053\u5177\uff01",
				"thumb_list": [{
					"url": "http://p3.pstatp.com/list/s270/150d0000ec7e5937f9ca",
					"url_list": [{
						"url": "http://p3.pstatp.com/list/s270/150d0000ec7e5937f9ca"
					}, {
						"url": "http://pb2.pstatp.com/list/s270/150d0000ec7e5937f9ca"
					}, {
						"url": "http://pb3.pstatp.com/list/s270/150d0000ec7e5937f9ca"
					}],
					"uri": "list/s270/150d0000ec7e5937f9ca",
					"height": 270,
					"width": 270,
					"is_gif": false
				}],
				"max_screen_width_percent": 0.6,
				"create_time": 1483872353,
				"type": 4,
				"media_type": 5,
				"group_id": 6373177732877074690,
				"online_time": 1483872353,
				"middle_image": {
					"width": 400,
					"url_list": [{
						"url": "http://p3.pstatp.com/w400/150d0000ec7e5937f9ca"
					}, {
						"url": "http://pb2.pstatp.com/w400/150d0000ec7e5937f9ca"
					}, {
						"url": "http://pb3.pstatp.com/w400/150d0000ec7e5937f9ca"
					}],
					"uri": "w400/150d0000ec7e5937f9ca",
					"height": 279
				},
				"min_screen_width_percent": 0.167
			},
			"online_time": 1483927634,
			"comments": [],
			"type": 1
		}, {
			"group": {
				"user": {
					"user_id": 53664361478,
					"name": "\u5934\u50cf\u6f02\u4eae\u5427",
					"followings": 0,
					"user_verified": false,
					"ugc_count": 2753,
					"avatar_url": "http://p3.pstatp.com/thumb/1354000fb3c1fdbe521f",
					"followers": 267,
					"is_following": false,
					"is_pro_user": false
				},
				"text": "",
				"neihan_hot_start_time": "00-00-00",
				"dislike_reason": [{
					"type": 1,
					"id": 302,
					"title": "\u7cd7\u4eba\u7cd7\u4e8b"
				}, {
					"type": 2,
					"id": 10,
					"title": "\u5427:\u7206\u7b11GIF"
				}, {
					"type": 4,
					"id": 0,
					"title": "\u5185\u5bb9\u91cd\u590d"
				}, {
					"type": 3,
					"id": 53664361478,
					"title": "\u4f5c\u8005:\u5934\u50cf\u6f02\u4eae\u5427"
				}],
				"create_time": 1483808834,
				"id": 53925190085,
				"favorite_count": 1392,
				"go_detail_count": 346686,
				"user_favorite": 0,
				"share_type": 1,
				"max_screen_width_percent": 0.6,
				"is_can_share": 1,
				"comment_count": 3747,
				"share_url": "http://m.neihanshequ.com/share/group/53925190085/?iid=7164180604&app=joke_essay",
				"label": 1,
				"content": "",
				"category_type": 1,
				"id_str": "53925190085",
				"media_type": 2,
				"share_count": 5505,
				"type": 3,
				"status": 112,
				"has_comments": 0,
				"large_image": {
					"width": 300,
					"r_height": 168,
					"r_width": 300,
					"url_list": [{
						"url": "http://p3.pstatp.com/large/13b9000326c34c4acc07"
					}, {
						"url": "http://pb2.pstatp.com/large/13b9000326c34c4acc07"
					}, {
						"url": "http://pb3.pstatp.com/large/13b9000326c34c4acc07"
					}],
					"uri": "large/13b9000326c34c4acc07",
					"height": 168
				},
				"user_bury": 0,
				"activity": {},
				"status_desc": "\u70ed\u95e8\u6295\u7a3f",
				"quick_comment": false,
				"display_type": 0,
				"neihan_hot_end_time": "00-00-00",
				"is_gif": 1,
				"user_digg": 0,
				"online_time": 1483808834,
				"category_name": "\u7206\u7b11GIF",
				"category_visible": true,
				"bury_count": 632,
				"is_anonymous": false,
				"repin_count": 1392,
				"min_screen_width_percent": 0.167,
				"is_neihan_hot": false,
				"digg_count": 38485,
				"gifvideo": {
					"360p_video": {
						"width": 300,
						"url_list": [{
							"url": "http://ic.snssdk.com/neihan/video/playback/?video_id=be6ba3dd2014494ba027a2c90dc9fcda&quality=360p&line=0&is_gif=1"
						}, {
							"url": "http://ic.snssdk.com/neihan/video/playback/?video_id=be6ba3dd2014494ba027a2c90dc9fcda&quality=360p&line=1&is_gif=1"
						}],
						"uri": "360p/be6ba3dd2014494ba027a2c90dc9fcda",
						"height": 168
					},
					"origin_video": {
						"width": 300,
						"url_list": [{
							"url": "http://ic.snssdk.com/neihan/video/playback/?video_id=be6ba3dd2014494ba027a2c90dc9fcda&quality=origin&line=0&is_gif=1"
						}, {
							"url": "http://ic.snssdk.com/neihan/video/playback/?video_id=be6ba3dd2014494ba027a2c90dc9fcda&quality=origin&line=1&is_gif=1"
						}],
						"uri": "origin/be6ba3dd2014494ba027a2c90dc9fcda",
						"height": 168
					},
					"720p_video": {
						"width": 300,
						"url_list": [{
							"url": "http://ic.snssdk.com/neihan/video/playback/?video_id=be6ba3dd2014494ba027a2c90dc9fcda&quality=720p&line=0&is_gif=1"
						}, {
							"url": "http://ic.snssdk.com/neihan/video/playback/?video_id=be6ba3dd2014494ba027a2c90dc9fcda&quality=720p&line=1&is_gif=1"
						}],
						"uri": "720p/be6ba3dd2014494ba027a2c90dc9fcda",
						"height": 168
					},
					"mp4_url": "http://ic.snssdk.com/neihan/video/playback/?video_id=be6ba3dd2014494ba027a2c90dc9fcda&quality=480p&line=0&is_gif=1.mp4",
					"video_height": 168,
					"480p_video": {
						"width": 300,
						"url_list": [{
							"url": "http://ic.snssdk.com/neihan/video/playback/?video_id=be6ba3dd2014494ba027a2c90dc9fcda&quality=480p&line=0&is_gif=1"
						}, {
							"url": "http://ic.snssdk.com/neihan/video/playback/?video_id=be6ba3dd2014494ba027a2c90dc9fcda&quality=480p&line=1&is_gif=1"
						}],
						"uri": "480p/be6ba3dd2014494ba027a2c90dc9fcda",
						"height": 168
					},
					"cover_image_uri": "13e400072d216965d96e",
					"duration": 15.1,
					"video_width": 300
				},
				"has_hot_comments": 0,
				"allow_dislike": true,
				"image_status": 1,
				"user_repin": 0,
				"neihan_hot_link": {},
				"group_id": 53925190085,
				"middle_image": {
					"width": 300,
					"r_height": 168,
					"r_width": 300,
					"url_list": [{
						"url": "http://p3.pstatp.com/w300/13b9000326c34c4acc07.webp"
					}, {
						"url": "http://pb2.pstatp.com/w300/13b9000326c34c4acc07.webp"
					}, {
						"url": "http://pb3.pstatp.com/w300/13b9000326c34c4acc07.webp"
					}],
					"uri": "w300/13b9000326c34c4acc07",
					"height": 168
				},
				"category_id": 10
			},
			"comments": [],
			"type": 1,
			"display_time": 1483927633.85,
			"online_time": 1483927633.85
		}
```
根据多次的校验，基本上bean可以这样写：
```
public MainItemBean(JSONObject jsonObject) {
        JSONObject group = jsonObject.optJSONObject("group");
        setContent("此处为广告，已为您过滤");
        if (group != null) {
            setTitle(group.optString("title"));
            setId(group.optLong("id"));
            setContent(group.optString("content"));

            if (!StringUtil.isNullOrEmpty(group.optString("mp4_url"))) {
                setMp4Url(group.optString("mp4_url"));
            }
            JSONObject gifvideo = group.optJSONObject("gifvideo");
            if (gifvideo != null) {
                setMp4Url(group.optString("mp4_url"));
            }

            JSONObject middle_image = group.optJSONObject("middle_image");
            if (middle_image != null) {
                setImage("http://p3.pstatp.com/" + middle_image.optString("uri"));
            }
            JSONObject large_image = group.optJSONObject("large_image");
            if (large_image != null) {
                setImage("http://p3.pstatp.com/" + large_image.optString("uri"));
            }
        }
    }
```
这里只写了核心代码片。ok了，看一下效果

* 运行效果<br>
![效果图](https://github.com/1181631922/Recreation/blob/master/ScreenShots/videos1.gif)
