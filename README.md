
# Lorem Picsum Browser App
The goal of this app is to implement the requirements of found at:\
https://gist.github.com/wbaumann/5a3524d5260a0ed056cc52b783726d3c

## Project overview:
Application is built using target SDK 30 with MVP architecture, use cases and repository pattern.\
Data fetched from API is cached with persistent cache and images are loaded (and cached) with Glide.\
Observer pattern is used for async calls using Rx library.\
Koin is used for dependency injection.\
Basic error handling and unit testing is done.\
This project's repository is under Git version control, in real app I would pay more attention\
to feature branches and pull requests. For now only master branch was used.\
Please read below for more details!

## Must have requirements:
* ✅ When the app launches, a loading 🌀 state should appear during the initial loading stage\
  (e.g. API requests are made)
* ✅ Once you've retrieved a valid list of images,\
  we should display each in a 2-column grid of square images
* ✅ Whenever the user taps on an image, a new screen (image detail)\
  should appear with the following: A larger view of the image and the name of the image's author.
* ✅ Additionally, the image detail screen should also include a button,\
  which will allow the user to share the image with other apps (e.g. email, SMS, etc)

## Optional requirements:
* ✅ Adding basic test coverage
* ✅ Handling error states if the API fails to load
* ✅ Adding support for a caching layer to speed up cold launches
* ✅ Adding support for paging, so we automatically load more images as the user scrolls up/down
* ✅ Using different amounts of grid columns, based on if the phone is in portrait vs landscape mode
* ✅ Using different amounts of grid columns, based on if we're using an iPhone or iPad
* ✅ Adding support for anything else that you think would be useful...

## Known problems:
* Wrong order when data is read from cache due to primitive shared preferences cache implementation\
  (hash map is used underneath). It would be fixed if we switched to proper persistent storage\
  for caching (ie. sql database)
* Images list restarts scroll position to top when back from detail page or when configuration changed.\
  This is due to used navigation component which by default recreates list fragment everytime.\
  It can surely be set to keep fragment instance but I didn't have time to do it or we could\
  save scroll position and restore it in new fragment instance. 

## Things to do later:
* Fixing above-mentioned issues
* Using better caching mechanism
* Prettifying UI
* Better error and retry handling
* Add listener for network availability changes and restarting loading when connection is back
* Test UI with instrumented tests
* Extract libraries used from build.gradle to separate files like versions.gradle.
