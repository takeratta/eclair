package fr.acinq.eclair.gui.controllers

import javafx.animation.{Interpolator, KeyFrame, KeyValue, Timeline}
import javafx.fxml.FXML
import javafx.scene.control.{Button, Label}
import javafx.scene.image.ImageView
import javafx.scene.layout.{HBox, Pane, VBox}
import javafx.util.Duration

import grizzled.slf4j.Logging

/**
  * Created by DPA on 22/09/2016.
  */
class SplashController extends Logging {

  @FXML var img: ImageView = _
  @FXML var imgBlurred: ImageView = _
  @FXML var splash: Pane = _
  @FXML var errorLabel: Label = _
  @FXML var closeButton: Button = _
  @FXML var errorBox: VBox = _

  /**
    * Start an animation when the splash window is initialized
    */
  @FXML def initialize() = {
    val t = new HBox()
    t.prefHeightProperty()

    val timeline = new Timeline()

    val startKF = new KeyFrame(Duration.ZERO,
      new KeyValue(img.opacityProperty, double2Double(0), Interpolator.EASE_IN),
      new KeyValue(imgBlurred.opacityProperty, double2Double(1.0), Interpolator.EASE_IN))

    val endKF = new KeyFrame(Duration.millis(1000.0d),
      new KeyValue(img.opacityProperty, double2Double(1.0), Interpolator.EASE_OUT),
      new KeyValue(imgBlurred.opacityProperty, double2Double(0), Interpolator.EASE_OUT))

    timeline.getKeyFrames.addAll(startKF, endKF)
    timeline.play()
  }

  def showError(message: String): Unit = {
    errorLabel.setText(message)
    errorBox.setOpacity(1)
  }

  @FXML def closeAndKill(): Unit = {
    System.exit(0)
  }
}