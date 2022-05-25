
export default function AddVariableFormCard (variables, setVariables) {
    const setVariableParameters = () => {

    }

    const variableCard = () => variables.map(variable =>
        <>
            {variable.name}
            <div>
                <div>
                    <label>Min</label>
                    <input type="number" name="min"/>
                </div>
                <div>
                    <label>Max</label>
                    <input type="number" name="question"/>
                </div>
                <div>
                    <label>Interval</label>
                    <input type="number" name="question"/>
                </div>
                <div>
                    <label>Add set value</label>
                    <input type="text"/>
                </div>
            </div>
        </>
    )
    return (
        <div>
            {variableCard}
        </div>
    )
}