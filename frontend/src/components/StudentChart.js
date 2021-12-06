import React, { useState, useEffect } from 'react'
import {PieChart,Pie,ResponsiveContainer} from "recharts"
import _ from "lodash"

const StudentChart = ({students,majors}) => {

    const [majorDatas, setMajorDatas] = useState([])

    useEffect(() => {
        setMajorDatas([])
        if(students.length>0) {
            majors.forEach(major => {
                let data = {
                    "name": major.name,
                    "value": _.filter(students, {'major':major}).length
                }
                setMajorDatas(majorDatas => [...majorDatas,data])
            });
        }

    }, [students,majors])

    return (
        <div>
            <ResponsiveContainer width="100%" height={400}>
                <PieChart>
                    <Pie data={majorDatas} dataKey="value" nameKey="name"  outerRadius={120} fill="#8884d8"
                    label={
                        (e) => {
                            return e.name+" "+e.value
                        }
                    } />
                </PieChart>
            </ResponsiveContainer>
        </div>
    )
}

export default StudentChart
