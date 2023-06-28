import Handlebars from "handlebars/runtime.js";
import AuthorDetails from "../author-details.hbs";

Handlebars.registerPartial("AuthorDetails", AuthorDetails);
export default {
    title: "Components/Author Details",
    argTypes: {},
};

export { AuthorDetails as Default };
