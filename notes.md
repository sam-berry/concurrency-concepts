# Concurrency Notes

## Based on Oracle documentation: https://docs.oracle.com/javase/tutorial/essential/concurrency

* A technique called time slicing is used in single threaded multi tasking systems. It defines a slice of time for a process to run before moving on to another scheduled process. From a client perspective it can appear as parallel processing.

* Processes and Threads. Threads exist within a process.

* Preferable to use `Runnable` over `Thread`

* `join` causes the current thread to sleep until joined thread is finished

