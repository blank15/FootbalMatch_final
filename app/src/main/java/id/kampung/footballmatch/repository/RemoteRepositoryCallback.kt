package id.kampung.footballmatch.repository

interface RemoteRepositoryCallback<T> {
    fun onDataLoaded(data: T?)
    fun onDataError(error: String?)
}