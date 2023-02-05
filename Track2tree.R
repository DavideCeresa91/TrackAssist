
# This code was written by
# Dr. Davide Ceresa, PhD
# IRCCS Ospedale Policlinico San Martino, 16132 Genova, Italy
# email: davide.ceresa91@gmail.com
# and
# Prof. Paolo Malatesta, PhD 
# IRCCS Ospedale Policlinico San Martino, 16132 Genova, Italy
# Department of Experimental Medicine (DIMES), University of Genova, 16132 Genova, Italy
# email: paolo.malatesta@unige.it
# The code is intended for academic purposes only


timeconv <- 0.360

linManTrack_file_path <- ""

Track2tree<-function(track=linManTrack_file_path){

	dati<-read.csv(track)
	dat<-dati
	dat$Slide<-dati$Slide*timeconv
	par(mfrow=c(2,2))
	for(i in 0:max(dat$Lineage)){
		#x11()
		IDset<-unique(dat$Cellula[dat$Lineage==i])
		startID<-tapply(dat$Slide[dat$Lineage==i],dat$Cellula[dat$Lineage==i],min)
		endID<-tapply(dat$Slide[dat$Lineage==i],dat$Cellula[dat$Lineage==i],max)
		altezza<-ordina(IDset)
		
		par(xaxt="n")
		plot(altezza,startID,ylim=rev(c(0,max(endID))),type="n",ylab="time (s x 10^3)",xlab="",frame.plot=F)
		for(t in IDset){
			lines(c(altezza[IDset==t],altezza[IDset==t]),c(endID[IDset==t],startID[IDset==t]))
			lines(c(altezza[IDset==t],altezza[IDset==floor(t/2)]),c(startID[IDset==t],endID[IDset==floor(t/2)]))
			#lines(c(endID[IDset==t],endID[IDset==floor(t/2)]),c(altezza[IDset==t],altezza[IDset==floor(t/2)]))
		}
		celdead<-!(tapply(dat$Dead[dat$Lineage==i],dat$Cellula[dat$Lineage==i],any))
		vm<-as.numeric(celdead)
		points(altezza,endID,pch=-4*(vm-1)+20*vm)
		
		
		
		
		
	}
}


ordina<-function(x){ 
	b<-2^(-a)
	c<-2*b
	p<-b+c*(x-2^a)
	return(p)
}


