import Form from "../fe-sign-up-form.hbs";
import CallBackForm from "../fe-call-back-form.hbs";

export default {
    title: "FE Components/form",
    argTypes: {
        sponsor: {
            options: ["Boeing", "ATT"],
            control: { type: "radio" },
        },
    },
};

const TemplateSignup = ({ label, ...args }) => Form();
export const SignUp = TemplateSignup.bind();

const TemplateCallBack = ({ label, ...args }) => CallBackForm();
export const CallBack = TemplateCallBack.bind();
