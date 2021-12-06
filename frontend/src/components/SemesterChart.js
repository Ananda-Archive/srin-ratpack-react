import React, { useState, useEffect } from 'react'
import {PieChart,Pie,ResponsiveContainer} from "recharts"
import _ from "lodash"

const SemesterChart = ({students}) => {

    const semesterList = [
        { id: 1, val: "Semester 1" },
        { id: 2, val: "Semester 2" },
        { id: 3, val: "Semester 3" },
        { id: 4, val: "Semester 4" },
        { id: 5, val: "Semester 5" },
        { id: 6, val: "Semester 6" },
        { id: 7, val: "Semester 7" },
        { id: 8, val: "Semester 8" },
    ]

    const [semesters, setSemesters] = useState([])

    useEffect(() => {
        setSemesters([])
        if(students.length>0) {
            semesterList.forEach(semester => {
                let data = {
                    "name": semester.val,
                    "value": _.filter(students, {'semester':semester.id}).length
                }
                setSemesters(semesters => [...semesters,data])
            });
        }

    }, [students])

    return (
        <div>
            <ResponsiveContainer width="100%" height={400}>
                <PieChart>
                    <Pie data={semesters} dataKey="value" nameKey="name"  outerRadius={120} fill="#8884d8"
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

export default SemesterChart
