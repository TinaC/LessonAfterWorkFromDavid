cordova-plugin-echo
-------------------

根据这个教程进行实现：
https://cordova.apache.org/docs/en/latest/guide/hybrid/plugins/index.html#building-a-plugin

add plugin https://github.com/AppGyver/steroids-echo-plugin
发现这个plugin的plugin.xml中注册标签有误，应该是：
  <!-- android -->
  <platform name="android">
      <config-file target="res/xml/config.xml" parent="/*">
      <feature name="Echo">
        <param name="android-package" value="com.appgyver.plugin.Echo"/>
        <param name="onload" value="true" />
      </feature>
    </config-file>

    <source-file src="src/android/com/appgyver/plugin/Echo.java"
      target-dir="src/com/appgyver/plugin" />
  </platform>

少了feature标签

以及，如何引入插件的js文件：
<script type="text/javascript" src="js/index.js"></script>
<script src="plugins/com.appgyver/plugin.Echo/www/EchoPlugin.js"></script>

通过完整的项目代码可以看到各个文件的位置。