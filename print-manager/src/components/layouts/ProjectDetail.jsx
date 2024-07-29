import { memo } from "react";
import { Link, useParams } from "react-router-dom";

export const ProjectDetail = memo(() => {
    const {projectId} = useParams();
    console.log('Project id:',projectId);

    return (
        <div>
            Detail
            <Link to={`/projects/${projectId}/designs`}><button className="btn btn-warning">Design</button></Link>
        </div>
    )
})