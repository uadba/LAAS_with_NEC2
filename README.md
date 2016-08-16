Linear Antenna Array Synthesizer (LAAS)
=

<h1>Documentation and Help</h1>
<h2 id="linear-antenna-array-synthesizer">Linear Antenna Array Synthesizer (LAAS)</h2>
In this document, the properties and usage of the linear antenna array synthesizer (LAAS) is presented to help the user.

<div class="toc">
<ul>
<li><a href="#linear-antenna-array-synthesizer">Linear Antenna Array Synthesizer</a><ul>
<li><a href="#starting-optimization">Starting Optimization</a></li>
<li><a href="#basic-procedure">Basic Procedure</a></li>
</ul>
</li>
<li><a href="#settings">Settings</a><ul>
<li><a href="#array-parameters-pane">Array Parameters Pane</a></li>
<li><a href="#outer-and-inner-mask-panes">Outer and Inner Mask Panes</a></li>
<li><a href="#basic-rules-for-masking">Basic Rules For Masking</a></li>
<li><a href="#differential-evolution-pane">Differential Evolution Pane</a></li>
</ul>
</li>
<li><a href="#display-numerically">Display Numerically</a><ul>
<li><a href="#messages-pane">Messages Pane</a></li>
</ul>
</li>
<li><a href="#file-operations">File Operations</a><ul>
<li><a href="#file-operations-pane">File Operations Pane</a></li>
</ul>
</li>
<li><a href="#graphs">Graphs</a><ul>
<li><a href="#antenna-array-pattern">Antenna Array Pattern</a></li>
<li><a href="#convergence-curve-plot">Convergence Curve Plot</a></li>
</ul>
</li>
</ul>
</div>

<h2 id="starting-optimization">Starting Optimization</h2>

In order to start optimization, it is enough to press `start optimization` button. However, before starting an optimization process, antenna array parameters and masks should be prepared properly. *Especially at least one mask has to be prepared. Otherwise, there is no cost function to minimize.*

![enter image description here](http://silisyum.com/assets/01_start_optimization.png)

After starting optimization, it can be paused with the same button. Pausing optimization process gives three options: showing the current results, terminating the optimization and resuming the optimization. If the show `current results` button is pressed, the current results including amplitude, phase and position values will be shown in the message pane. Terminating an optimization by pressing `terminate optimization` button means that you cancel the optimization and it cannot be resumed; it has to be started from the beginning if you want to run it again. To resume the optimization, `continue optimization` button must be pressed.

![enter image description here](http://silisyum.com/assets/02_continue_optimization.png)

<h2 id="basic-procedure">Basic Procedure</h2>

The following procedure may be useful for the basic optimization task in the LAAS.

1. **Setting the basic antenna array parameters.**
	Determining how many antenna array elements and which array parameters are used for the optimization can be done in [Array Parameters Pane](#array-parameters-pane).

2. **Creating masks**
	The outer and inner masks can be prepared in [Outer and Inner Mask Panes](#outer-and-inner-mask-panes). At least one must be defined but both masks together are not mandatory. For example, if you don't need inner mask you may not use it.

3. **Starting the optimization.**
	`start optimization` button is used to start an optimization with the current configuration. Same button can be used for pausing and resuming tasks in the relevant states.

<h1 id="settings">Settings</h1>

<h2 id="array-parameters-pane">Array Parameters Pane</h2>

This section is about antenna array parameters planned to be optimized.
> **Set the Number of Antenna Array Elements**
> You can set the number of antenna array elements by pressing this button. You will see a simple dialog box after doing that. You simply enter the number and press onto "OK" button. Then all parameters in the screen will be changed according to this number, such as the number of amplitude, phase and position values as well as the shape of the pattern.
>
> ![enter image description here](http://silisyum.com/assets/03_number_of_elements.png)

> **Amplitude, Phase, and Position Check Boxes**
> If you select any type of parameter check box, you mean that you want it to be calculated by optimization algorithm. If you make it unchecked, you mean that you leave it fixed and it will not be changed by the algorithm. However, they will take part in the calculations with their fixed values. These values can be entered manually in the list or loaded from a text file.
>
> ![enter image description here](http://silisyum.com/assets/04_check_boxes.png)

> **Maximum and Minimum Values of the Parameters**
> Each parameter is limited within a minimum and a maximum values. These limitation values can be changed. This is especially useful for the phase and position perturbation values. It is worth to note that the maximum and minimum values for the position section are actually position perturbation values not the absolute position values. The distance between each array elements can normally be $0.5 \lambda$ but if the position perturbation optimization is selected, the meta-heuristic algorithm will optimize them within the maximum and minimum perturbation values.
>
> ![enter image description here](http://silisyum.com/assets/05_max_min.png)

> **Resetting Parameter Values to Ones, Zeros, and Half-wavelength**
> Default values of amplitude and phase in LAAS are ones and zeros, respectively. The position values of the array elements follow the rule that each element has $0.5 \lambda$ distance to the adjacent elements and the position of the first element is zero. When the user presses one of the reset parameter values buttons, the values will be reset to the according default values which have been explained above.
>
	> ![enter image description here](http://silisyum.com/assets/06_reset_amplitude_phase_position_values.png)

> **Entering Parameter Values Manually in the List**
> You can manually enter any parameter values in the list at the `Array Parameters` section after making unchecked the relevant checkbox. It can be useful for the small number of antenna array elements but if the number of elements is not so small, loading the values from a text file may be more reasonable choice.
>
> ![enter image description here](http://silisyum.com/assets/07_list_of_amplitude_phase_position_values.png)

> **Load Parameter Values From a File**
> Each array element parameter values, namely, amplitude, phase and position values, can be loaded from a text file by pressing the relevant button. After pressing the button, you will see a file chooser dialog box. You can select a text file having values in a column.
>
> ![enter image description here](http://silisyum.com/assets/08_loading_text.png)
>
> Each value can be separated from each other in a column by simply pressing enter in the text file. Technically, this loading function accepts the following characters as line terminators:
> `\u000D followed by \u000A, CARRIAGE RETURN followed by LINE FEED`
> `\u000A, LINE FEED`
> `\u000D, CARRIAGE RETURN`
> You can watch [this tutorial video](#) to see how you can load any parameter data from a text file.
>

<h2 id="outer-and-inner-mask-panes">Outer and Inner Mask Panes</h2>

Mask segments constitutes the outer and inner masks. They can be any number. They have  five important properties: *start angle*, *stop angle*, *number of points*, *level* and *weight*.

![enter image description here](http://silisyum.com/assets/09_add_mask_segment.png)

Obviously, start and stop angle values are the starting point and stopping point of a mask segment on the angle scale. The number of points is used to determine the sample points of this segment. The bigger number of points means the more precise calculations. However, the big number of samples causes more computational cost. The user should consider this trade-off. Level defines the mask level in dB to restrict the pattern. The level value of an outer mask segment is a maximum limit for the pattern. Similarly, The level value of an inner mask segment is a minimum limit for the pattern. weight is a factor to amplify the difference between the mask and obtained pattern at the relevant point. The level and weight values are automatically registered for all points after using mask adding and editing dialog boxes. Additionally, one can change their values for each points separately in the selected mask segment values table if needed.

<h2 id="basic-rules-for-masking">Basic Rules For Masking</h2>

- Mask segments must not overlap with each other
- Start angle must be smaller than stop angle
- Every segment has at least two points
- Mask segment name must be different from the the other segment in the current list
- Level value is allowed to be zero or negative
- Weight value must be bigger than zero. It is usually one.
> **Adding New Mask Segments**
> You can add a mask segment by pressing `Add` button.
>
> ![enter image description here](http://silisyum.com/assets/10_add_edit_delete.png)
>
> Once you press it, you will see an adding a new mask segment dialog box. Mask segment name is just a name to identify the mask segment which is about to be made up.  A start angle value is normally zero for the first segment but this is not a rule because any segment can be added any time. The stop angle must be bigger than start angle. Number of points must be at least two because a segment has at least one start angle and one stop angle. Level value is expected to be a negative value because the array pattern in dB is produced from normalized values. Weight can be any positive decimal number. If there is no need to intensify any point, one can be used as a standard value for the all number of points throughout the mask. If there is another segment which is added previously, this former segment's stop angle is offered as the new segment's start angle. In the same way, the previous level and weight values are also offered for the new segment. User can simply change these values if required. Mask segments must not overlap each other, otherwise the new segment that causes overlapping will not be added.
>
> **Editing Mask Segments**
> Editing a mask segment is very similar to adding new segment procedure. Same rules are valid for it. When a segment is made shorter, user should be careful about the possible gabs between this segment and the adjacent segments. Similarly, when a segment is made longer, it should not be forgotten that segments cannot be overlapped.
>
> **Deleting a Mask Segment**
> A segment can be deleted by simply selecting it in the list and clicking delete button above the list.

<h2 id="differential-evolution-pane">Differential Evolution Pane</h2>

> Classical differential evolution (DE) algorithm has four main parameters: population number, maximum iteration number, scaling factor (F), and crossover rate (Cr). These values can be entered and edited in this pane.
>
> ![enter image description here](http://silisyum.com/assets/11_DE.png)

<h1 id="display-numerically">Display Numerically</h1>

<h2 id="messages-pane">Messages Pane</h2>

> Any warning and error messages for the user is shown in the field in this pane. The results of the optimization values are shown here at the end of the optimization or whenever the user wants to see by pressing show current results during the pausing states of the optimization.
> ![enter image description here](http://silisyum.com/assets/12_messages.png)

<h1 id="file-operations">File Operations</h1>

<h2 id="file-operations-pane">File Operations Pane</h2>

> **Configuration Files**
> The configurations of the LAAS can be saved into a file with a "*.aas*" extension. The configuration includes all antenna array and differential evolution algorithm parameters as well as outer and inner mask segments. Once a configuration file is saved, it can be loaded later by pressing `load configuration from the a file` button in the same pane. `Reset configuration to default` button is used for resetting the current configuration to the default LAAS values.
>
> ![enter image description here](http://silisyum.com/assets/13_config_files.png)
>
> **Exporting SVG Files**
> Scalable Vector Graphics (SVG) is a two-dimensional vector image format. One can benefit from the advantage of the vector image format of the both antenna array pattern and convergence curve by exporting them in this pane. Though the pattern and convergence curve images in PNG format can be saved by right-clicking on the relevant chart, the scalable property of the SVG format provides better flexibility.
>
> ![enter image description here](http://silisyum.com/assets/14_svg_export.png)
>
> The exported SVG images can be modified and exported into the other formats by using free and open-source vector graphics editor <a href="https://inkscape.org/" target="_blank">Inkscape</a>

<h1 id="graphs">Graphs</h1>

<h2 id="antenna-array-pattern">Antenna Array Pattern</h2>

This pane presents the antenna array pattern with current values of the antenna array elements. These values can be initialization values entered by the user or the calculated values by the DE algorithm. The pattern will be shown repeatedly during the optimization algorithm runs. The final results also appeared here at the end of the optimization. The graph also includes the outer and inner masks in different colors.

![enter image description here](http://silisyum.com/assets/15_pattern.png)

> **Rescaling Pattern Graph**
> The pattern graph can be rescaled by pressing *rescale pattern graph* button. This command consider the values from zero to what the user enters in the relevant text field next to the button. Builtin zoom-in property of the graph can be used to see more details by using the mouse pointer on the graph field.
>
> **Updating Frequency of Pattern Graph**
> The array pattern is updated as often as possible by default. If the user wants to change the update frequency for any reason, the LAAS provides seven different options for this aim in a drop-down list. This options are *as often as possible, every 1 second, every 3 seconds, every 7 seconds, every 15 seconds, every 1 minute, and only at the beginning and end*.
>
> **Plotting Pattern with Different Resolutions**
> The LAAS provides an opportunity to draw pattern in four different constant resolutions. These resolutions are made up of 1441, 721, 361, and 181 points. Apart from these options, the user can see the pattern in the outer and inner mask resolutions. These two resolution options depend on the number of mask sample points. If there is no mask, no pattern can be seen when the mask points option is selected. The advantage of the mask points resolution options is that one can see the real relationship between mask and pattern calculated by the algorithm. The other constant resolutions may not represent the same points depending upon the user mask definitions.

<h2 id="convergence-curve-plot">Convergence Curve Plot</h2>

In this pane, the convergence curve provided by DE algorithm is presented. This pane also shows the current iteration number and cost value numerically. Adjusting the updating frequency of convergence graph is provided in this pane, too.

![enter image description here](http://silisyum.com/assets/16_convergence_curve.png)
