# Tinkoff News
A news viewer app based on Tinkoff API

<li><strong>Offline first</strong> -- all data available once you downloaded it, offline mode doesn't kill app features</li>
<li><strong>Bookmarks tab</strong> -- save your favourite news in one place</li>
<li><strong>Lightweight</strong> -- almost no libraries were used (Dagger 2 + RxJava 2)</li>
<li><strong>Smooth (maybe)</strong> -- News Feed list is drawn on a custom ViewGroup widget, Text is measured on a background thread</li>

# Screenshots
<p>
<a href="https://github.com/Lounah/TinkoffNews/blob/develop/screenshots/screen_feed.jpg" target="_blank">
  <img src="https://github.com/Lounah/TinkoffNews/blob/develop/screenshots/screen_feed.jpg" width="270" height="480" alt="Screenshot" style="max-width:100%;">
</a>
<a href="https://github.com/Lounah/TinkoffNews/blob/develop/screenshots/screen_story_details.jpg" target="_blank">
  <img src="https://github.com/Lounah/TinkoffNews/blob/develop/screenshots/screen_story_details.jpg" width="270" height="480" alt="Screenshot" style="max-width:100%;">
</a>
<a href="https://github.com/Lounah/TinkoffNews/blob/develop/screenshots/screen_bookmarks.jpg" target="_blank">
  <img src="https://github.com/Lounah/TinkoffNews/blob/develop/screenshots/screen_bookmarks.jpg" width="270" height="480" alt="Screenshot" style="max-width:100%;">
</a>
<a href="https://github.com/Lounah/TinkoffNews/blob/develop/screenshots/screen_advanced.jpg" target="_blank">
  <img src="https://github.com/Lounah/TinkoffNews/blob/develop/screenshots/screen_advanced.jpg" width="270" height="480" alt="Screenshot" style="max-width:100%;">
</a>
</p>

# Libraries
<li> <a href="">RxJava 2</a></li>
<li> <a href="">Dagger 2</a></li>
<li> <a href="">Gson</a></li>
<li> <a href="">Timber</a></li>

# Download
https://yadi.sk/d/Pq-k3lkPo12-fQ -- Release
https://yadi.sk/d/LDM5NCMWA3ypuw -- Debug

# Feedback & Questions
Feel free to ask me anything here: t.me/lounvh

# Примечания
Изначально планировал написать два приложения, одно было бы с Rx, Dagger'ом, Room и Retrofit, а второе -- совсем без библиотек
Потом понял, что на это не хватит времени, и решил выпилиывать из проекта те библиотеки, функционал которых смогу написать руками
В итоге использовал SqlLite (уродливо и неудобно, кстати), выпилил Retrofit в конце, а за Даггер взялся поздно, наверное, нужно было сразу
писать без него

Постарался сделать более менее плавным -- написал кастомную вью для элемента списка, заюзал precomputed text compat (хотя, кстати,
все это особого прироста в перфомансе не дало, возможно, я что-то делаю не так)

Есть костыли (хендлинг добавления в закладки на экране StoryDetails и сохранение скролла)
Пейджинг, кстати, тоже так себе сделан, только потом уже увидел, что апи, оказывается, его поддерживает

Тесты написал
