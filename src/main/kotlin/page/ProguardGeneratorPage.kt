package page

import ProguardGeneratorUtil
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import tool.FileUtil
import java.io.File

/**
 * @auth 二宁
 * @date 2023/11/24
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ProguardGeneratorPage() {
    var outputDir by remember { mutableStateOf<String>(FileUtil.getDesktopFile().absolutePath) }
    var lineNum by remember { mutableStateOf(8000) }
    var charLength by remember { mutableStateOf(6) }
    var specialChar by remember { mutableStateOf("0oOQ") }

    Column(modifier = Modifier.padding(10.dp).fillMaxWidth().background(Color.White).padding(10.dp)) {
        Row {
            Text(modifier = Modifier.align(Alignment.CenterVertically), text = "输出目录：")
            TextField(
                colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.White),
                shape = RectangleShape,
                modifier = Modifier.fillMaxWidth(),
                value = outputDir,
                onValueChange = { outputDir = it },
                singleLine = true,
                placeholder = { Text("输出目录") },
                isError = outputDir.isBlank()
            )
        }
        Row(modifier = Modifier.padding(top = 10.dp)) {
            Text(modifier = Modifier.align(Alignment.CenterVertically), text = "行数：")
            TextField(
                colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.White),
                shape = RectangleShape,
                modifier = Modifier.align(Alignment.CenterVertically).width(100.dp),
                value = "$lineNum",
                onValueChange = { lineNum = it.toIntOrNull() ?: 8000 },
                singleLine = true,
                placeholder = { Text("行数") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
            )
            Text(modifier = Modifier.align(Alignment.CenterVertically).padding(start = 10.dp), text = "长度：")
            TextField(
                colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.White),
                shape = RectangleShape,
                modifier = Modifier.align(Alignment.CenterVertically).width(100.dp),
                value = "$charLength",
                onValueChange = { charLength = it.toIntOrNull() ?: 6 },
                singleLine = true,
                placeholder = { Text("长度") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
            )
            Text(modifier = Modifier.align(Alignment.CenterVertically).padding(start = 10.dp), text = "特殊字符：")
            TextField(
                colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.White),
                shape = RectangleShape,
                modifier = Modifier.align(Alignment.CenterVertically).width(120.dp),
                value = specialChar,
                onValueChange = { specialChar = it },
                singleLine = true,
                placeholder = { Text("特殊字符") },
            )
        }

        FlowRow(modifier = Modifier.padding(top = 10.dp)) {
            Button(onClick = {
                ProguardGeneratorUtil.LetterProguard.start(File(outputDir), lineNum, charLength)
            }, content = {
                Text("生成混淆字典")
            })
            Button(modifier = Modifier.padding(start = 10.dp), onClick = {
                ProguardGeneratorUtil.CharacterProguard.start(File(outputDir), lineNum, *specialChar.toCharArray())
            }, content = {
                Text("生成特殊字符字典")
            })
            Button(modifier = Modifier.padding(start = 10.dp), onClick = {
                ProguardGeneratorUtil.ChineseProguard.start(File(outputDir))
            }, content = {
                Text("生成中文字典")
            })
        }
    }
}