package xyz.rh.enjoyframent.jsonparser.gson

/**
 * Created by rayxiong on 2023/9/7.
 */

// List<Map<String, Hobby>>外层是一个数组，里面包含的内容是map组成的；需要用typetoken解析
const val typeTokenData = """
    [
        {   "hobby_map1": {
                "h1": {
                    "name": "看动画片",
                    "level": 0
                },
                "h2": {
                    "name": "switch",
                    "level": 1
                }
            }
        },
        {   "hobby_map2": {
                "h21": {
                    "name": "2看动画片",
                    "level": 0
                },
                "h22": {
                    "name": "2switch",
                    "level": 21
                }
            }
        }
    ]
"""

// List<Hobby>外层是一个数组，里面包含Hobby；需要用typetoken解析
const val typeTokenData2 = """
    [
        {
           "name": "看动画片",
           "level": 0,
           "innermap" : {
                "mapk1" : 100,
                "mapk2" : 200
           }
        },
        {
           "name": "switch",
           "level": 1,
           "innermap" : {
                "mapk3" : 300,
                "mapk4" : 400
           }
        }
    ]
"""

// 特意将 name 返回为错误的类型：例如 true， []， {} 等，用原始Gson.from就会报错，崩溃
const val dataError = """
    {
        "name" : [],
        "age": "18",
        "hobby_map": {
            "h1": {
                "name": "看动画片",
                "level": 0
            },
            "h2": {
                "name": "switch",
                "level": 1
            },
            "h3": {
                "name": "eatting",
                "level": 3
            },
            "h4": 100
        }
    }
"""

const val data = """
    {
        "name" : "dodo",
        "age": "18",
        "hobby_map": {
            "h1": 12,
            "h2": 13,
            "h3": 14,
            "h4": 100
        },
        "tail_property": "this is the end"
    }
"""

// 少了age的返回
const val data2 = """
    {
        "name" : "dodo",
        "hobby_map": {
            "h1": {
                "name": "看动画片",
                "level": 0
            },
            "h2": {
                "name": "switch",
                "level": 1
            },
            "h3": {
                "name": "eatting",
                "level": 3
            },
            "h4": 100
        }
    }
"""

// 少了name，少了age：解析后name不会走默认的，而是null；name也不会走默认的-1，而是0
const val data3 = """
    {
        "hobby_map": {
            "h1": {
                "name": "看动画片",
                "level": 0
            },
            "h2": {
                "name": "switch",
                "level": 1
            },
            "h3": {
                "name": "eatting",
                "level": 3
            },
            "h4": 100
        }
    }
"""

// 额外加了map嵌套list的复杂类型
const val data4 = """
    {
        "name" : "dodo",
        "age" : 18,
        "hobby_map": {
            "h1": {
                "name": "看动画片",
                "level": 0
            },
            "h2": {
                "name": "switch",
                "level": 1
            },
            "h3": {
                "name": "eatting",
                "level": 3
            },
            "h4": 100
        },
        "lovers" : {
            "damimi" : [80,66,90],
            "avnv" : [10,20,30]
        }
    }
"""