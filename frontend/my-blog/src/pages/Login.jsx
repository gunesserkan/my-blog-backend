import React, { useState } from 'react'
import { useDispatch } from 'react-redux'
import '../css/login.css'
import { login } from '../redux/slices/authSlice'


import FormLogin from '../components/forms/FormLogin'
import FormRegister from '../components/forms/FormRegister'

function Login({ isLogin }) {
    const dispatch = useDispatch()

    const handleLogin = () => {
        dispatch(login({ username, password }));
        const token = localStorage.getItem("bearerToken")
        // navigate("/")
        console.log("bearer token: " + token)
    }

    //TODO: login ve register sayfalarını yap
    return (
        <div className='login-container'>
            <FormLogin onSubmit={handleLogin} />
        </div>
    )
}

export default Login