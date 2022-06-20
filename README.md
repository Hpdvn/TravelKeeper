# TravelKeeper

Java android project - Traveler companion, remember and share the place you visited 🚶‍♂️.

L'application permet à ses utilisateurs de prendre des photos 📷 des lieux emblématiques visités 🗽, d'y ajouter un nom, un commentaire, et place automatiquement sur une carte un point correspondant a la position géographique 🗺️ (longitude & latitude) de l'endroit ou a été pris le cliché.

- Toutes ces informations sont stockées en base de données, ce qui permet notamment à l'utilisateur, de retrouver la liste des lieux enregistrés lorsqu'il arrive sur la page d'accueil de l'application. L'interfacage entre la base de données et l'application se fait via un DAO, permettant de traiter les enregistrement de la base de données comme une liste d'objets Java.

- Une utilisation de toutes les caméras du téléphone de l'utilisateur est également nécessaire pour pouvoir prendre des clichés : l'utilisateur doit approuver l'utilisation de ces périphériques via une fenetre popup lors de l'utilisation de l'application, il est également possible pour l'utilisateur de sélectionner une image depuis la galerie de son téléphone si ce dernier souhaite utiliser une photo déjà prise, ou ne souhaite pas donner a l'application le droit d'utiliser les caméras de son appareil.

- L'application utilise l'api google map afin d'afficher la carte et de pouvoir y placer des points via leurs coordonnées, comme pour les appareils photo, cette fonctionnalité nécessite que l'utilisateur actionne manuellement, via une fenetre popup, l'autorisation d'utilisation du gps du téléphone.


