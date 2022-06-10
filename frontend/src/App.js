import React, {useEffect, useState} from 'react';
import './App.css';
import {BrowserRouter as Router, Route, Routes} from 'react-router-dom';
import Navbar from "./components/Navbar";
import Home from './pages';
import Search from "./pages/Search/Search";
import AttemptSolution from "./pages/AttemptSolution/AttemptSolution";
import SubmitQuestion from "./pages/SumbitQuestion/SubmitQuestion";
import {loadingStatuses} from "./loadingStatuses";
import {serverURL} from "./serverURL";

export default function App({axios}) {
    const [topics, setTopics] = useState([])
    const [topicsStatus, setTopicsStatus] = useState(loadingStatuses.isLoading)

    const getTopics = () => {
        axios.get(`${serverURL}/topics`)
            .then((res) => {
                setTopics(res.data)
            })
            .then(() => {
                setTopicsStatus(loadingStatuses.loadedSuccessfully)
            })
            .catch(() => {
                console.log(`failed`)
                setTopicsStatus(loadingStatuses.loadingFailed)
            })
    }

    useEffect(() => {
        if (topicsStatus === loadingStatuses.isLoading) {
           getTopics()
        }
    })

    return (
        <Router>
            <Navbar/>
            <Routes>
                <Route exact path='/' element={<Home/>}/>
                <Route path='/search' element={<Search axios={axios} topics={topics} topicStatus={topicsStatus}/>}/>
                <Route path='/attemptSolution' element={<AttemptSolution/>}/>
                <Route path='/submitQuestion' element={<SubmitQuestion axios={axios}/>}/>
            </Routes>
        </Router>
    )
}
