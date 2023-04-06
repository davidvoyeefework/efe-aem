import Handlebars from "handlebars/runtime.js";
import CarouselImage  from '../image-slides.hbs';
import CarouselTeaser  from '../teaser-slides.hbs';
import CarouselAutoTransition  from '../auto-transition.hbs';
import CarouselPauseOnHover  from '../pause-hover.hbs';
Handlebars.registerPartial("CarouselImage", CarouselImage);
export default {
  title: 'Core Components/Carousel',
  argTypes: {
  },
};

const TemplateImage = ({ label, ...args }) => CarouselImage({ ...args });
export const Image = TemplateImage.bind();

const TemplateTeaser = ({ label, ...args }) => CarouselTeaser({ ...args });
export const Teaser = TemplateTeaser.bind();

const TemplateAutoTransition = ({ label, ...args }) => CarouselAutoTransition({ ...args });
export const AutoTransition = TemplateAutoTransition.bind();

const TemplatePauseOnHover = ({ label, ...args }) => CarouselPauseOnHover({ ...args });
export const PauseOnHover = TemplatePauseOnHover.bind();
