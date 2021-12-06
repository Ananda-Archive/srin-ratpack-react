import axios from "axios"
import { baseUrl } from "./BaseUrl"

const TABLE_NAME = "major"
const header = {
    'Content-Type': 'application/json'
}

const api = {

    async getAllMajor() {
        try {
            const res = await axios.get(baseUrl + TABLE_NAME, { headers: header })
            return res.data
        } catch (err) {
            return err
        }
    }

}; export default api