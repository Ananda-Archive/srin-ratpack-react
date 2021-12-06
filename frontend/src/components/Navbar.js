import { AppBar,Container,Toolbar,Box,Button } from '@mui/material'
import CircleRoundedIcon from '@mui/icons-material/CircleRounded';

import React from 'react'

const Navbar = () => {

    // Variable
    const menus = [
        {id:1, name:"Home", link:"/"},
        {id:2, name:"Student", link:"/student"}
    ]

    return (
        <AppBar position="static">
            <Container maxWidth="xl" sx={{mx:0}}>
                <Toolbar>
                    {/* Icon */}
                    <CircleRoundedIcon sx={{fontSize:30, color:"#7E2483"}}/>
                    <CircleRoundedIcon sx={{fontSize:30, color:"#5BBFE5"}}/>
                    <CircleRoundedIcon sx={{mr:4, fontSize:30, color:"#F19EB6"}}/>

                    {/* Navbar Menu */}
                    {menus.map((menu) => (
                        <Box key={menu.id}>
                            <Button href={menu.link} sx={{color:"white", display:"block", fontSize:"large", my:2, mr:2}}>
                                {menu.name}
                            </Button>
                        </Box>
                    ))}

                </Toolbar>
            </Container>
        </AppBar>
    )
}

export default Navbar
