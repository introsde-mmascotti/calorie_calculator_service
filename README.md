# Calorie Calculator Service
This RESTful webservice calculates the BMR (basal metabolic rate) and the daily recommended calory intake using the Harris Benedict equation
(see https://en.wikipedia.org/wiki/Harris%E2%80%93Benedict_equation).

##BMR

BMR = (10 * weight) + (6,25 * height) - (5 * age)

for men: BMR   + 5  
for women: 	BMR - 161

##Recommended daily intake
Multiplies the obtained BMR value with a physical activity factor (PAL - factor). The PAL factor indicates how active a person is.
