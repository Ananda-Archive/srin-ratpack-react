import axios from "axios"
import { baseUrl } from "./BaseUrl"

const TABLE_NAME = "student"
const header = {
    'Content-Type': 'application/json'
}

const api = {

    async getAllStudents() {
        try {
            const res = await axios.get(baseUrl + TABLE_NAME, { headers: header })
            return res.data
        } catch (err) {
            return err
        }
    },

    async createStudent(student) {
        try {
            let data = {
                ...student, majorId:student.major.id
            }
            delete data.major
            const res = await axios.post(baseUrl + TABLE_NAME, data, { headers: header })
            return res.data
        } catch (err) {
            return err
        }
    },

    async seedStudents(n) {
        try {
            const res = await axios.post(baseUrl + TABLE_NAME + "/" + n, { headers: header })
            return res.data
        } catch (err) {
            return err
        }
    },

    async updateStudent(student) {
        try {
            let data = {
                ...student, majorId:student.major.id
            }
            delete data.major
            const res = await axios.put(baseUrl + TABLE_NAME, data, { headers: header })
            return res.data
        } catch (err) {
            return err
        }
    },

    async deleteStudent(id) {
        try {
            const res = await axios.delete(baseUrl + TABLE_NAME + "/" + id, { headers: header })
            return res.data
        } catch (err) {
            return err
        }
    },

    createStudentUsingPromise(student) {
        let data = {
            ...student, majorId:student.major.id
        }
        delete data.major
        return new Promise((resolve, reject) => {
            axios.post(baseUrl + TABLE_NAME, data, { headers: header })
                .then((res) => {
                    resolve(res.data)
                }) .catch((err) => {
                    reject(err)
                })
        })
    },

    updateStudentUsingPromise(student) {
        let data = {
            ...student, majorId:student.major.id
        }
        delete data.major
        return new Promise((resolve, reject) => {
            axios.put(baseUrl + TABLE_NAME, data, { headers: header })
                .then((res) => {
                    resolve(res.data)
                }) .catch((err) => {
                    reject(err)
                })
        })
    },

    deleteStudentUsingPromise(id) {
        return new Promise((resolve, reject) => {
            axios.delete(baseUrl + TABLE_NAME + "/" + id, { headers: header })
                .then((res) => {
                    resolve(res.data)
                }) .catch((err) => {
                    reject(err)
                })
        })
    }

}; export default api