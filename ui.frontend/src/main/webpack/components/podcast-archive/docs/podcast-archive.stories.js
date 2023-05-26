import Handlebars from "handlebars/runtime.js";
import PodcastArchive from "../podcast-archive.hbs";
Handlebars.registerPartial("PodcastArchive", PodcastArchive);

export default {
  title: "Components/PodcastArchive",
  argTypes: {},
};

const TemplatePodcastArchive = (args) => PodcastArchive({...args});
export const podcastArchive = TemplatePodcastArchive.bind({});
