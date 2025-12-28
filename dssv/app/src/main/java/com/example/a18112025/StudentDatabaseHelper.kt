package com.example.a18112025

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class StudentDatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "StudentMgr.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "students"

        // Tên các cột
        private const val COL_ID = "studentId" // Khóa chính (MSSV)
        private const val COL_NAME = "name"
        private const val COL_PHONE = "phoneNumber"
        private const val COL_ADDRESS = "address"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = ("CREATE TABLE $TABLE_NAME ("
                + "$COL_ID TEXT PRIMARY KEY,"
                + "$COL_NAME TEXT,"
                + "$COL_PHONE TEXT,"
                + "$COL_ADDRESS TEXT)")
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    // 1. Lấy toàn bộ danh sách sinh viên
    fun getAllStudents(): MutableList<Student> {
        val list = mutableListOf<Student>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME", null)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getString(cursor.getColumnIndexOrThrow(COL_ID))
                val name = cursor.getString(cursor.getColumnIndexOrThrow(COL_NAME))
                val phone = cursor.getString(cursor.getColumnIndexOrThrow(COL_PHONE))
                val address = cursor.getString(cursor.getColumnIndexOrThrow(COL_ADDRESS))
                list.add(Student(id, name, phone, address))
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return list
    }

    // 2. Thêm sinh viên
    fun addStudent(student: Student): Long {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COL_ID, student.studentId)
        values.put(COL_NAME, student.name)
        values.put(COL_PHONE, student.phoneNumber)
        values.put(COL_ADDRESS, student.address)

        val success = db.insert(TABLE_NAME, null, values)
        db.close()
        return success
    }

    // 3. Cập nhật sinh viên (Dựa theo MSSV cũ - nhưng ở đây MSSV là khóa chính)
    // Lưu ý: Logic bài này MSSV là ID, nên ta update các trường khác dựa trên ID
    fun updateStudent(student: Student): Int {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COL_NAME, student.name)
        values.put(COL_PHONE, student.phoneNumber)
        values.put(COL_ADDRESS, student.address)

        val success = db.update(TABLE_NAME, values, "$COL_ID=?", arrayOf(student.studentId))
        db.close()
        return success
    }

    // 4. Xóa sinh viên
    fun deleteStudent(studentId: String): Int {
        val db = this.writableDatabase
        val success = db.delete(TABLE_NAME, "$COL_ID=?", arrayOf(studentId))
        db.close()
        return success
    }
}