

import React, { useState, useEffect } from 'react'
import MajorService from "../services/MajorService"
import StudentService from "../services/StudentService"
import StudentChart from "../components/StudentChart"
import SemesterChart from "../components/SemesterChart"
import {Grid,Typography} from "@mui/material"

const Home = () => {

    // State
    // Data
    const [majors, setMajors] = useState([])
    const [students, setStudents] = useState([])

    // =======

    // Use Effect Event
    // Init UseEffect
    useEffect(() => {
        getAllStudents()
        getAllMajors()
    }, [])

    // Service Function
    const getAllMajors = () => {
        MajorService.getAllMajor()
            .then((res) => setMajors(res))
    }
    const getAllStudents = () => {
        StudentService.getAllStudents()
            .then((res) => {
                setStudents(res)
            })
    }

    return (
        <div >
            <Grid container spacing={10} sx={{mt:5}}>
                <Grid item xs={6}>
                    <Typography variant="h3" gutterBottom component="div" align="center">
                        Major Chart
                    </Typography>
                    <StudentChart students={students} majors={majors} />
                </Grid>
                <Grid item xs={6}>
                    <Typography variant="h3" gutterBottom component="div" align="center">
                        Semester Chart
                    </Typography>
                    <SemesterChart students={students} />
                </Grid>
            </Grid>
        </div>
    )
}

export default Home
