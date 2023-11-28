package xyz.rh.enjoyfragment.temp

import java.util.regex.Pattern

/**
 * Created by rayxiong on 2022/12/8.
 */
open class TempTestKotlin {

    protected val assProtectedPa : String? = null

    inline fun testIns(block: () -> Unit) {
//        xlog("test inline::::: p1=$assProtectedPa")
        block.invoke()
    }


}

fun main() {

    println("hello 123")

//    val map = HashMap<String?, String?>()
//    map.put("1", "a")
//    map.put("2","b")
//    map.put("3", null)
//    map.put("4", null)
//    map.put("3", null)
//    map.put(null, null)
//    map.put(null, null)
//    map.put(null, null)
//    map.put("1","a")
//
//    println("size=${map.size}")
//
//    map.forEach {
//        println("itertor : key=${it.key}, value= ${it.value}")
//    }

//    val arrayList = mutableListOf<String>()

//    s = "c"
//    s = null

//    thread {
//        s = null
//    }

//    arrayList.add("a")
//    arrayList.add("b")
//
//    thread {
////        arrayList.clear()
////        curr(arrayList)
//
//        Thread.sleep(1)
//        arrayList.add("c")
//    }
//
//    thread {
////        arrayList.clear()
//        curr(arrayList)
//    }


    val regularExpression1 = "\\{[^}]*\\}"

    val regularExpression2 = "\\{{[^}}]*\\}"

    val testRegular = "\\{\\{((?:.|\\r?\\n)+?)\\}\\}"

    val doubleBracket = "\\{\\{(.+?)\\}\\}"
    val singleBracket = "\\{(.+?)\\}"


//    val pattern = Pattern.compile(test3)
//    val suffixMatcher = pattern.matcher("预估{{12.3 是不是这样子}}{~12.7}元")
//    suffixMatcher.find()
//    val suffixText = suffixMatcher.group()
//    println(suffixText)


    val aaa = "\\{([^}]*)\\}"

//    val str = "预估的{{12.3 是不是这样子}}{~12.7}元"
    val str = "预估的"

    var beginText = ""
    var firstContent = ""
    var secondContent = ""
    var thirdContent = ""

    val firstPattern = Pattern.compile(doubleBracket)
    val firstMatcher = firstPattern.matcher(str)
    firstMatcher.find()
    val firstWrapperText = firstMatcher.group()
    println(firstWrapperText) // {{12.3 是不是这样子}}
    if (firstWrapperText.length >= 4) {
        firstContent = firstWrapperText.substring(2, firstWrapperText.length-2)
        println(firstContent) // 12.3 是不是这样子
    }

    val firstIndex = str.indexOf(firstWrapperText)
//    println(firstIndex)

    if(firstIndex >= 0) {
        beginText = str.substring(0, firstIndex)
        println(beginText)
    }

    val secondPattern = Pattern.compile(singleBracket)
    val tail = str.substring((firstIndex + firstWrapperText.length))
    println(tail) //  {~12.7}元
    val secondMatcher = secondPattern.matcher(tail)
    secondMatcher.find()
    val secondWrapperText = secondMatcher.group()
    println(secondWrapperText)
    if (secondWrapperText.length >= 2) {
        secondContent = secondWrapperText.substring(1, secondWrapperText.length-1)
        println(secondContent)
    }

    val lastIndexOfBracket = str.lastIndexOf("}")
    if (lastIndexOfBracket >=0) {
        thirdContent = str.substring(lastIndexOfBracket + 1, str.length)
    }

    println(thirdContent)


    "预估{color=#000000 font=1 size=18 text=12.3}{color=#000000 font=2 size=12 text=~12.7}元"
    println("解析之后====$beginText{color=#000000 font=1 size=18 text=$firstContent}{color=#000000 font=2 size=12 text=$secondContent}$thirdContent")





    val text = "快车、特惠去{伊莎贝尔烘焙蛋糕(锦丰店)}"
    val beginIndex = text.indexOf("{")
    val beginContent = text.substring(0, beginIndex)
    val endContent = text.substring(beginIndex + 1, text.length-1)

    println("$beginContent   $endContent")


    val data = ""


//    println(buildAbuseFontProtocol(str) + "====")

}




private fun buildAbuseFontProtocol(abusiveStr: String) : String? {

    val doubleBracket = "\\{\\{(.+?)\\}\\}"
    val singleBracket = "\\{(.+?)\\}"

    var beginText = ""
    var firstContent = ""
    var secondContent = ""
    var thirdContent = ""

    var formatStr = ""

    try {
        val firstPattern = Pattern.compile(doubleBracket)
        val firstMatcher = firstPattern.matcher(abusiveStr)
        firstMatcher.find()
        val firstWrapperText = firstMatcher.group()
        if (firstWrapperText.length >= 4) {
            firstContent = firstWrapperText.substring(2, firstWrapperText.length-2)
        }

        val firstIndex = abusiveStr.indexOf(firstWrapperText)

        if(firstIndex >= 0) {
            beginText = abusiveStr.substring(0, firstIndex)
        }

        val secondPattern = Pattern.compile(singleBracket)
        val tail = abusiveStr.substring((firstIndex + firstWrapperText.length))
        val secondMatcher = secondPattern.matcher(tail)
        secondMatcher.find()
        val secondWrapperText = secondMatcher.group()
        if (secondWrapperText.length >= 2) {
            secondContent = secondWrapperText.substring(1, secondWrapperText.length-1)
        }

        val lastIndexOfBracket = abusiveStr.lastIndexOf("}")
        if (lastIndexOfBracket >=0) {
            thirdContent = abusiveStr.substring(lastIndexOfBracket + 1, abusiveStr.length)
        }
    } catch (e: Exception) {
        e.printStackTrace()
        return null
    }
    formatStr = "$beginText{color=#000000 font=2 size=18 text=$firstContent}{color=#000000 font=2 size=14 text=$secondContent}$thirdContent"
    println("解析之后====$formatStr")
    return formatStr
}


