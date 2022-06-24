import React from 'react'
import './Search.css'
import {loadingStatuses} from "../../loadingStatuses";
import {serverURL} from "../../serverURL";
import {useNavigate} from 'react-router-dom';


export default function Search({axios, setQuestions, units, topics, topicStatus}) {
    const navigate = useNavigate();

    const handleSearchByTopic = (topic) => {
        axios.get(`${serverURL}/topics/${topic.topicUuid}`)
            .then(res => setQuestions(res.data))
            .then(()=> navigate('/search/topic/'))
    }

    const renderCategories = () => {
        if (topicStatus === loadingStatuses.isLoading) {
            return <>loading</>
        } else if (topicStatus === loadingStatuses.loadingFailed) {
            return <>failed to load</>
        } else {
            return renderUnits()
        }
    }

    const renderUnits = () => {
        return units.map(unit =>
            <div key={unit.unitEnum} className="unit-container">
                <h3 className="unit-title">{unit.unit}</h3>
                <div className="topics-container">
                    {renderTopics(unit.unit)}
                </div>
            </div>
        )
    }

    const renderTopics = (unit) => {
        return topics.filter(topic => topic.unit === unit)
            .map((topic) =>
                <div key={topic.topicUuid} className="topic">
                    <p className="topic" onClick={() => handleSearchByTopic(topic)}>{topic.topic}</p>
                </div>
            )
    }

    return (
        <div className="search-body">
            <h2>Physics Topics</h2>
            <div>
                {renderCategories()}
            </div>
        </div>
    )
}