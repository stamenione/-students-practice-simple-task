import video from "../../assets/background-video.mp4";

export default function BackgroundVideo() {
    return (
        <div>
            <video autoPlay loop muted poster="../../assets/background-image.png">
                <source src={video} type="video/mp4" />
            </video>
        </div>
    );
}
