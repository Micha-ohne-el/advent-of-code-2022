import kotlinx.coroutines.flow.flow
import okio.Source
import okio.buffer

fun readLines(source: Source) = flow {
    source.use { fileSource ->
        fileSource.buffer().use { bufferedFileSource ->
            while (!bufferedFileSource.exhausted()) {
                emit(bufferedFileSource.readUtf8LineStrict())
            }
        }
    }
}
