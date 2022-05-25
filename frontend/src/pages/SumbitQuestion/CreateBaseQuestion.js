import {useState} from "react";
import AddVariableFormCard from "./AddVariableFormCard";

export default function CreateBaseQuestion({axios}) {
    const [topic, setTopic] = useState('')
    const [baseQuestion, setBaseQuestion] = useState('')
    const [baseEquation, setBaseEquation] = useState('')
    const [solutionUnit, setSolutionUnit] = useState('')
    const [currentVariable, setCurrentVariable] = useState('')
    const [variables, setVariables] = useState([])

    const handleChangeTopic = (e) => {
        setTopic(e.target.value)
    }
    const handleChangeQuestion = (e) => {
        setBaseQuestion(e.target.value)
    }
    const handleChangeCurrentVariable = (e) => {
        setCurrentVariable(e.target.value)
    }
    const handleAddVariable = (e) => {
        e.preventDefault()
        setVariables([...variables, {"name": currentVariable}])
        setCurrentVariable('')
    }
    const checkValues = (e) => {
        e.preventDefault()
        console.log(`Topic: ${topic} \n Question: ${baseQuestion} \n variables: ${variables.toString()}`)
    }

    return <>
        <form>
            <div>
                <label>Topic</label>
                <input type="text" name="topic" onChange={handleChangeTopic}/>
            </div>
            <div>
                <label>Question</label>
                <input type="text" name="question" onChange={handleChangeQuestion}/>
            </div>
            <div>
                <button onClick={(e) => handleAddVariable(e)}>Add variable</button>
                <input type="text" name="variable" value={currentVariable} onChange={handleChangeCurrentVariable}/>
            </div>
            <button onClick={(e) => checkValues(e)}>check values</button>
        </form>

        <div className="Add-variable-container">
            {variables.length > 0 ? <AddVariableFormCard variables={variables} setVeriables={setVariables} /> : null}
        </div>
    </>
}