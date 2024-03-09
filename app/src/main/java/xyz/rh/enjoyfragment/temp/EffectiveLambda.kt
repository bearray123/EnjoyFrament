package xyz.rh.enjoyfragment.temp

/**
 * Created by rayxiong on 2024/1/11.
 */


fun main() {
    println(123)
    asyncFetchData(1) {
        // 这里的onSuccess/onFail函数不是申明，而是函数调用，但这个函数调用内部走的其实是赋值操作，理解这点很关键~
        // 相当于走了onSuccess/onFail后就是给 ResultScope.()->Unit 这个block对象
        onSuccess {
            println("successs == $it")
        }
        onFail {
            println("failed === $it")
        }
    }
}


interface ResultScope {

    fun onSuccess(block: (data: String)->Unit)

    fun onFail(block: (data: String)->Unit)

}

class ResultScopeImpl: ResultScope {

    var successBlock : ((data: String)->Unit) = {}
    var failBlock : ((data: String)->Unit) = {}


    override fun onSuccess(block: (data: String) -> Unit) {
        successBlock = block
    }

    override fun onFail(block: (data: String) -> Unit) {
        failBlock = block
    }
}

fun asyncFetchData(config: Any, result: ResultScope.()->Unit) {
    val resultScopeImpl = ResultScopeImpl().apply(result)

    resultScopeImpl.run {
        if (config.equals(1)) {
            this.successBlock("成功了，data=1")
        } else {
            this.failBlock.invoke("失败了，错误码未知")
        }
    }


}