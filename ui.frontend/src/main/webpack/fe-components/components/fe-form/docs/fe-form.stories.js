import Form from "../fe-sign-up-form.hbs";
import CallBackForm from "../fe-call-back-form.hbs";
import Handlebars from "handlebars/runtime.js";
Handlebars.registerPartial("Form", Form);
Handlebars.registerPartial("CallBackForm", CallBackForm);
export default {
    title: "FE Components/form",
    argTypes: {
        variation: {
            options: ["Default", "theme-vanguard" , "theme-aon", "theme-ssga"],
            control: { type: "radio" },
        },
    },
};

const TemplateSignup = ({ label, ...args }) => Form({...args});
export const SignUp = TemplateSignup.bind();

const TemplateCallBack = ({ label, ...args }) => CallBackForm({...args});
export const CallBack = TemplateCallBack.bind();
