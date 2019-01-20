# begining of the the data analysis from a csv
# \copy observation to /home/loic/Desktop/filename.csv csv header


setwd("~/Desktop")
capteurs_data <- read.csv2("filename.csv", sep= ",", stringsAsFactors=FALSE, header = TRUE)
#View(capteurs_data)

tmpInside<-capteurs_data[which(capteurs_data$id_sensor == -1 & capteurs_data$id_measure_type == 3),]
plot(tmpInside$id,tmpInside$value)

tmpOutside<-capteurs_data[which(capteurs_data$id_sensor == -3 & capteurs_data$id_measure_type == 3),]
plot(tmpOutside$id,tmpOutside$value)

lumiInside<-capteurs_data[which(capteurs_data$id_sensor == -3 & capteurs_data$id_measure_type == 4),]
plot(lumiInside$id,lumiInside$value)
