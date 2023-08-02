package com.example.sudokusolver20

import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.sudokusolver20.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val matrix = arrayOf(
            arrayOf("", "", "", "", "", "", "", "", ""),
            arrayOf("", "", "", "", "", "", "", "", ""),
            arrayOf("", "", "", "", "", "", "", "", ""),
            arrayOf("", "", "", "", "", "", "", "", ""),
            arrayOf("", "", "", "", "", "", "", "", ""),
            arrayOf("", "", "", "", "", "", "", "", ""),
            arrayOf("", "", "", "", "", "", "", "", ""),
            arrayOf("", "", "", "", "", "", "", "", ""),
            arrayOf("", "", "", "", "", "", "", "", ""),
        )

        var arr = ArrayList<EditText>()
        binding.apply {
            arr.addAll(
                arrayOf(
                    a00, a01, a02, a03, a04, a05, a06, a07, a08,
                    a10, a11, a12, a13, a14, a15, a16, a17, a18,
                    a20, a21, a22, a23, a24, a25, a26, a27, a28,
                    a30, a31, a32, a33, a34, a35, a36, a37, a38,
                    a40, a41, a42, a43, a44, a45, a46, a47, a48,
                    a50, a51, a52, a53, a54, a55, a56, a57, a58,
                    a60, a61, a62, a63, a64, a65, a66, a67, a68,
                    a70, a71, a72, a73, a74, a75, a76, a77, a78,
                    a80, a81, a82, a83, a84, a85, a86, a87, a88,
                )
            )
        }
        binding.solve.setOnClickListener {
            var k = 0
            for (i in 0..8) {
                for (j in 0..8) {
                    if (arr[k].text.isNotEmpty()) {
                        matrix[i][j] = arr[k].text.toString()
                    }
                    k++
                }
            }
            if (!isValidConfig(matrix)) {
                Clear(matrix, arr)
                Toast.makeText(this@MainActivity, "Invalid Sudoku", Toast.LENGTH_SHORT)
                    .show()
            } else {
                solve(matrix, 0, 0)
                k = 0
                for (i in 0..8) {
                    for (j in 0..8) {
                        arr[k].setText(matrix[i][j])
                        k++
                    }
                }
            }


        }
        binding.clear.setOnClickListener {
            Clear(matrix, arr)
        }


    }

    private fun Clear(matrix: Array<Array<String>>, arr: ArrayList<EditText>) {
        var k = 0
        for (i in 0..8) {
            for (j in 0..8) {
                matrix[i][j] = ""
                arr[k].setText("")
                k++
            }
        }
    }

    private fun solve(matrix: Array<Array<String>>, i: Int, j: Int): Boolean {
        if (i == 9) return true
        if (j == 9) return solve(matrix, i + 1, 0)
        if (matrix[i][j] != "") return solve(matrix, i, j + 1)



        for (it in 1..9) {
            if (isvalid(matrix, i, j, it)) {
                matrix[i][j] = "$it"
                if (solve(matrix, i, j + 1)) return true
                matrix[i][j] = ""
            }

        }
        return false
    }

    private fun isvalid(matrix: Array<Array<String>>, i: Int, j: Int, value: Int): Boolean {
        val row = i - (i % 3)
        val col = j - (j % 3)

        for (x in 0..8) if (matrix[x][j] == "$value") return false
        for (y in 0..8) if (matrix[i][y] == "$value") return false
        for (x in 0..2) for (y in 0..2) if (matrix[row + x][col + y] == "$value"
        ) return false
        return true

    }


    fun notInRow(matrix: Array<Array<String>>, row: Int): Boolean {

        val st = HashSet<String>()
        for (i in 0..8) {

            if (st.contains(matrix[row][i])) return false

            if (matrix[row][i] != "") st.add(matrix[row][i])
        }
        return true
    }


    fun notInCol(arr: Array<Array<String>>, col: Int): Boolean {
        val st = HashSet<String>()
        for (i in 0..8) {

            if (st.contains(arr[i][col])) return false

            if (arr[i][col] != "") st.add(arr[i][col])
        }
        return true
    }

    fun notInBox(
        arr: Array<Array<String>>,
        startRow: Int,
        startCol: Int
    ): Boolean {
        val st = HashSet<String>()
        for (row in 0..2) {
            for (col in 0..2) {
                val curr = arr[row + startRow][col + startCol]

                if (st.contains(curr)) return false

                if (curr != "") st.add(curr)
            }
        }
        return true
    }

    fun isValidConfig(arr: Array<Array<String>>): Boolean {
        val n=arr.size
        for (i in 0..n - 1) {
            for (j in 0..n - 1) {
                if (arr[i][j]!="" && !valid(arr, i, j)) return false
            }
        }
        return true
    }

    private fun valid(arr: Array<Array<String>>, row: Int, col: Int): Boolean {
        return (arr[row][col] !in "1".."9") && notInRow(arr, row) && notInCol(arr, col) &&
                notInBox(arr, row - row % 3, col - col % 3)
    }

}
