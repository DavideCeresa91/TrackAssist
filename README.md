# TrackAssist

This ImageJ plugin is designed to streamline manual cell tracking in time-lapse image stacks.

The plugin enables users to mark cell positions, track cell lineages, manage cell divisions, and save tracking information in a CSV file.

Features

	Track cells in image stacks
	Record and store cell divisions and cell deaths
	Store cell positions and lineage information
	Output data to a CSV file

Installation

	Download Mi_Track.class and Mi_Track.lut files.
	Place the Mi_Track.class file in the ImageJ plugins folder.
	Place the Mi_Track.lut file in the ImageJ luts folder.
	Restart ImageJ to load the plugin.
 
Usage

	To use the plugin, follow these steps:
	Open the image stack in ImageJ.
	Run the Mi_Track plugin.
	
	Interact with the image using the key combinations:
	
	Ctrl + click: Trace the cell position and move stack forward by 1 step
	Shift + click: Trace the cell position and move stack forward by 4 steps
	Shift + Ctrl + click: Mark a cell division
	Shift + Alt + click: Go back to the last division and track the other sister cell
	Alt + click: Mark a cell death
	Ctrl + Alt + click: End the analysis and write the output file
	
	Destructive commands
	
	Use these commands with caution as they may remove data:
	Ctrl + Alt + Shift + click: Go to the ancestor of the current cell, and remove the current branch and its sister
	Shift + Meta + click: Erase the current point
				
Output

	The plugin generates a CSV file containing the following columns:
	
		Cellula:  Position in lineage tree. Ancestor cells have a value of 1.
			  and upon division, daughter cells receive values calculated as 
			  (parent value) * 2 and (parent value) * 2 + 1, enabling precise lineage tracking.
		X:        X-coordinate of the cell in the image
		Y:        Y-coordinate of the cell in the image
		Slide:    Image slice number
		Lineage:  Lineage identifier of the cell, marks different ancestor cells in the first image of the stack.
		Actual:   Indicates if the cell position is manually tracked (true) or interpolated between two manually tracked timepoints (false)
		Dead:     Indicates if the cell is dead (true) or not (false)
	
	
Generating lineage trees from CSV files
	Open Track2tree.R file.
	Run the entire script in R.
	Generate a lineage tree by running the command `Track2tree([output csv file path])`.


