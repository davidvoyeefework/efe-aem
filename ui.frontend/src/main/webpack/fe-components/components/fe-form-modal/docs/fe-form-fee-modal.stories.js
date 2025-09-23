import FeeModal from "../fe-form-fee-modal.hbs";
import ErrorModal from "../fe-error-modal.hbs";
export default {
    title: "FE Components/form modal",
    argTypes: {
        variation: {
            options: ["Default", "theme-vanguard" , "theme-aon", "theme-ssga", "theme-empower"],
            control: { type: "radio" },
        },
    },
};

const TemplateFormFeeModal = ({ label, ...args }) => FeeModal({...args});
export const FormFeeModal = TemplateFormFeeModal.bind();

const TemplateErrorModal = ({ label, ...args }) => ErrorModal({...args});
export const FormErrorModal = TemplateErrorModal.bind();

