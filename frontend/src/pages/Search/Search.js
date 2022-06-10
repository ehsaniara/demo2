import React, {useState} from 'react'
import './Search.css'
import {loadingStatuses} from "../../loadingStatuses";

export default function Search({topics, topicStatus}) {
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
        let units = []
        topics.map(topic => {
            if (units[units.length - 1] !== topic.unit) {
                units.push(topic.unit)
            }
        })

        return units.map(unit =>
            <div key={unit} className="unit-container">
                <h3 className="unit-title">{unit}</h3>
                <div className="topics-container">
                    {renderTopics(unit)}
                </div>
            </div>
        )
    }

    const renderTopics = (unit) => {
        return topics.filter(topic => topic.unit === unit)
            .map((topic) => <p key={topic.topicUuid} className="topic">{topic.topic}</p>)
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