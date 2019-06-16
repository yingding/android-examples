# Android Room with a View - Java  
Example for using ViewModel, LiveData and Room
* https://codelabs.developers.google.com/codelabs/android-room-with-a-view/index.html#0

# Solution code can be downloaded from
https://github.com/googlecodelabs/android-room-with-a-view

# Room with multiple processes
If your app runs in multiple processes, include enableMultiInstanceInvalidation() in
your database builder invocation. That way, when you have an instance of AppDatabase in
each process, you can invalidate the shared database file in one process, and this invalidation
automatically propagates to the instances of AppDatabase within other processes.
https://developer.android.com/training/data-storage/room

# Paging library
If you have lots of data, consider using the paging library
https://developer.android.com/topic/libraries/architecture/paging.html

# TODOs
* (open) adding the testing codes according to the Solution code

# Further Resources
* [Android Persistence codelab](https://codelabs.developers.google.com/codelabs/android-persistence/#0) (LiveData, Room, DAO)
* [Andorid lifecycle-aware components codelab](https://codelabs.developers.google.com/codelabs/android-lifecycles/#0) (ViewModel, LiveData, LifecycleOwner, LifecycleRegistryOwner)
* [Architecture Component code samples](https://github.com/googlesamples/android-architecture-components)